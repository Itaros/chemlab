package ru.itaros.hoe.coremod;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fluids.Fluid;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import ru.itaros.hoe.fluid.HOEFluid;

public class FluidHOEConversionFieldInjector implements IClassTransformer {

	public static boolean isInstrumented=false;
	
	@Override
	public byte[] transform(String className, String transformedName,
			byte[] basicClass) {
		if(className.equals("ru.itaros.hoe.fluid.FluidToHOE")){
			System.out.println("Filling FluidToHOE class");

			//Prepare transplants
			final Type hoeFluidDescriptor = Type.getType(HOEFluid.class);
			
			ClassReader cr = new ClassReader(basicClass);
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
			ClassVisitor cv = new ClassVisitor(Opcodes.ASM4,cw){				
				
				@Override
				public MethodVisitor visitMethod(int access, String name,
						String desc, String signature, String[] exceptions) {
					if(name.equals("get")|name.equals("set")|name.equals("isActive")){
						if(name.equals("get")){
							
							MethodVisitor mv = cv.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "get", "(Lnet/minecraftforge/fluids/Fluid;)"+hoeFluidDescriptor.getDescriptor(), null, null);
							mv.visitCode();
							Label l0 = new Label();
							mv.visitLabel(l0);
							mv.visitLineNumber(27, l0);
							mv.visitVarInsn(Opcodes.ALOAD, 0);
							mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "net/minecraftforge/fluids/Fluid", "getHoeFluidAssociation", "()"+hoeFluidDescriptor.getDescriptor());
							mv.visitInsn(Opcodes.ARETURN);
							Label l1 = new Label();
							mv.visitLabel(l1);
							mv.visitLocalVariable("f", "Lnet/minecraftforge/fluids/Fluid;", null, l0, l1, 0);
							mv.visitMaxs(1, 1);
							mv.visitEnd();
							
							return mv;
							
						}
						if(name.equals("set")){
							
							MethodVisitor mv = cv.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "set", "(Lnet/minecraftforge/fluids/Fluid;"+hoeFluidDescriptor.getDescriptor()+")Lnet/minecraftforge/fluids/Fluid;", null, null);
							mv.visitCode();
							Label l0 = new Label();
							mv.visitLabel(l0);
							mv.visitLineNumber(22, l0);
							mv.visitVarInsn(Opcodes.ALOAD, 0);
							mv.visitVarInsn(Opcodes.ALOAD, 1);
							mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "net/minecraftforge/fluids/Fluid", "setHoeFluidAssociation", "("+hoeFluidDescriptor.getDescriptor()+")Lnet/minecraftforge/fluids/Fluid;");
							mv.visitInsn(Opcodes.POP);
							Label l1 = new Label();
							mv.visitLabel(l1);
							mv.visitLineNumber(23, l1);
							mv.visitVarInsn(Opcodes.ALOAD, 0);
							mv.visitInsn(Opcodes.ARETURN);
							Label l2 = new Label();
							mv.visitLabel(l2);
							mv.visitLocalVariable("f", "Lnet/minecraftforge/fluids/Fluid;", null, l0, l2, 0);
							mv.visitLocalVariable("hoefl", hoeFluidDescriptor.getDescriptor(), null, l0, l2, 1);
							mv.visitMaxs(2, 2);
							mv.visitEnd();
							
							return mv;
						}
						if(name.equals("isActive")){
							
							MethodVisitor mv = cv.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "isActive", "()Z", null, null);
							mv.visitCode();
							Label l0 = new Label();
							mv.visitLabel(l0);
							mv.visitLineNumber(23, l0);
							mv.visitInsn(Opcodes.ICONST_1);
							mv.visitInsn(Opcodes.IRETURN);
							mv.visitMaxs(1, 0);
							mv.visitEnd();
							
							return mv;
							
						}
						
						
						return null;
						
					}else{
						return super.visitMethod(access, name, desc, signature, exceptions);
					}
				}

				
				
				
			};
			
			
			//cv.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "isActive", "()Z", null, null);
			//cw.v
			cv.visit(Opcodes.V1_7, Opcodes.ACC_PUBLIC+Opcodes.ACC_SUPER, "ru/itaros/hoe/fluid/FluidToHOE", null, null, null);
			cr.accept(cv, 0);
			
			
			basicClass=cw.toByteArray();
			
		}
		
		if(className.equals("net.minecraftforge.fluids.Fluid")){
			System.out.println("Adding direct optimizations for HOEFluids into Forge Fluids...");
			
			//Preparing tools
			ClassReader cr = new ClassReader(basicClass);
			final ClassWriter cw = new ClassWriter(cr,ClassWriter.COMPUTE_MAXS);
			
			//Prepare transplants
			Type hoeFluidDescriptor = Type.getType(HOEFluid.class);
			
			//INJECT \o/
			//Field...
			cw.visitField(Opcodes.ACC_PROTECTED, "hoeFluidAssociation", hoeFluidDescriptor.getDescriptor(), null,null).visitEnd();
			//Accessors...
			createSetter(cw,internalizeName(className),"hoeFluidAssociation",hoeFluidDescriptor);
			createGetter(cw,internalizeName(className),"hoeFluidAssociation",hoeFluidDescriptor);
			//cw.visitEnd();
			//Finalize
			cw.visitEnd();
			cr.accept(cw,0);
			
			isInstrumented=true;
			
			basicClass=cw.toByteArray();
			
		}
		
		return basicClass;
	}

	String internalizeName(String className){
		return className.replace('.', '/');
	}
	
	void createSetter(ClassVisitor cw, String internalClassName, String propertyName, Type c) {
			String methodName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
			MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, methodName, "(" + c.getDescriptor() + ")Lnet/minecraftforge/fluids/Fluid;", null, null);
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitVarInsn(c.getOpcode(Opcodes.ILOAD), 1);
			mv.visitFieldInsn(Opcodes.PUTFIELD, internalClassName, propertyName, c.getDescriptor());
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitInsn(Opcodes.ARETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}

		void createGetter(ClassVisitor cw, String internalClassName, String propertyName, Type c) {
		  String methodName = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
		  MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, methodName, "()" + c.getDescriptor(), null, null);
		  mv.visitVarInsn(Opcodes.ALOAD, 0);
		  mv.visitFieldInsn(Opcodes.GETFIELD, internalClassName, propertyName, c.getDescriptor());
		  mv.visitInsn(c.getOpcode(Opcodes.IRETURN));
		  mv.visitMaxs(0, 0);
		  mv.visitEnd();
		}	
	
}
